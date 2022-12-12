package ru.karmazin.vkpostspringmvc.model;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiAuthException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ApiWallAddPostException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.wall.responses.PostResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.karmazin.vkpostspringmvc.repository.GroupRepository;
import ru.karmazin.vkpostspringmvc.repository.PostRepository;
import ru.karmazin.vkpostspringmvc.repository.TimeRepository;
import ru.karmazin.vkpostspringmvc.repository.VkAccountRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Karmazin
 */
@Getter
@Setter
@Component
@Slf4j
@RequiredArgsConstructor
public class PostProcessTask implements Runnable {
    private final TimeRepository timeRepository;
    private final PostRepository postRepository;
    private final GroupRepository groupRepository;
    private final VkAccountRepository vkAccountRepository;

    private TransportClient transportClient;
    private VkApiClient vk;
    private UserActor userActor;
    private VkAccount vkAccount;
    private List<Group> groupList;
    private Post post;

    @Override
    public void run() {
        if (initUserData())
            return;
        if (post != null) {
            if (!post.getStarted()) {
                log.info("Не удалось запустить процесс, post не существует");
            }
            initVk();

            startPosting();
        }
    }

    private boolean initUserData() {
        if (post == null) {
            postRepository.findById(1L).ifPresent(value -> post = value);
        }
        if (vkAccount == null) {
            Optional<VkAccount> vkAccountOptional =
                    vkAccountRepository.findBySelected(true);
            if (vkAccountOptional.isEmpty()) {
                log.info("Нет выбранного аккаунта");
                post.setStarted(false);
                log.info("Отключение автопостинга");
                return true;
            }
            vkAccount = vkAccountOptional.get();
            log.info("Выбранный аккаунт: {}", vkAccount.getName());
        }
        if (groupList == null) {
            groupList = groupRepository.findAll();
        }
        return false;
    }

    private void initVk() {
        log.info("Инициализация Vk Api");
        if (transportClient == null)
            transportClient = new HttpTransportClient();
        if (vk == null)
            vk = new VkApiClient(transportClient);
        if (userActor == null) {
            userActor = new UserActor(
                    vkAccount.getUser_id(),
                    vkAccount.getAccess_token()
            );
        }
    }

    private void startPosting() {
        log.info("Старт постов");
        log.info("Группы:");
        for (Group group : groupList) {
            log.info("{}", group.getName());
            try {
                PostResponse postResponse = vk.wall().post(userActor)
                        .ownerId(group.getGroup_id())
                        .message(post.getText())
                        .attachments(post.getPhoto_id())
                        .execute();
                log.info("Отправлен пост: {}",postResponse);
            } catch (ApiWallAddPostException e) {
                log.warn("Бан в группе: {}", group.getName());
                groupList.remove(group);
                if (groupList.size() == 0) {
                    log.warn("Все группы забанены");
                    post.setStarted(false);
                    log.info("Отключение автопостинга");
                    break;
                }
            } catch (ApiAuthException e) {
                log.error("Ошибка аутентификации");
                post.setStarted(false);
                log.error("{}", e.getMessage());
                throw new RuntimeException(e);
            } catch (ApiException | ClientException e) {
                log.error("Необработанная ошибка");
                post.setStarted(false);
                throw new RuntimeException(e);
            }
        }
    }
}
