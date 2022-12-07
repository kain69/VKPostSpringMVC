package ru.karmazin.vkpostspringmvc.model;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ApiWallAddPostException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.wall.responses.PostResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.karmazin.vkpostspringmvc.repository.GroupRepository;
import ru.karmazin.vkpostspringmvc.repository.PostRepository;
import ru.karmazin.vkpostspringmvc.repository.TimeRepository;
import ru.karmazin.vkpostspringmvc.repository.VkAccountRepository;

import java.util.List;

/**
 * @author Vladislav Karmazin
 */
@Getter
@Setter
@Component
public class PostProcessTask implements Runnable{
    @Autowired
    private TimeRepository timeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private VkAccountRepository vkAccountRepository;

    private TransportClient transportClient;
    private VkApiClient vk;
    private UserActor userActor;
    private VkAccount vkAccount;
    private List<Group> groupList;
    private Post post;

    @Override
    public void run() {
        if(post == null){
            postRepository.findById(1L).ifPresent(value -> post = value);;
        }
        if(post != null){
            if(post.getStarted()){
                initFields();

                startPosting();
            }
        }
    }

    private void initFields() {
        if(transportClient == null)
            transportClient = new HttpTransportClient();
        if(vk == null)
            vk = new VkApiClient(transportClient);
        if(userActor == null){
            userActor = new UserActor(
                    vkAccount.getUser_id(),
                    vkAccount.getAccess_token()
            );
        }
        if(groupList == null){
            groupList = groupRepository.findAll();
        }
    }

    private void startPosting() {
        for (Group group : groupList) {
            try {
                PostResponse postResponse = vk.wall().post(userActor)
                        .ownerId(group.getGroup_id())
                        .message(post.getText())
                        .attachments(post.getPhoto_id())
                        .execute();
                System.out.println(postResponse);
            }
            catch (ApiWallAddPostException e){
                groupList.remove(group);
                if(groupList.size() == 0){
                    System.out.println("Все группы забанены");
                    post.setStarted(false);
                    break;
                }
            }
            catch (ApiException | ClientException  e) {
                throw new RuntimeException(e);
            }
        }
    }
}
