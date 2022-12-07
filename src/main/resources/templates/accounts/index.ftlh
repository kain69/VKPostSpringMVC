<#import "../parts/common.ftlh" as c>

<@c.page>
    <table class="table table-bordered table-sm">
        <thead class="">
        <tr>
            <th style="width: 25%">name</th>
            <th style="width: 25%">user_id</th>
            <th style="width: 30%">access_token</th>
            <th style="width: 20%">action</th>
        </tr>
        </thead>
        <tbody>
        <#foreach account in accounts>
            <tr>
                <td>${account.name}</td>
                <td>${account.user_id}</td>
                <td>${account.access_token}</td>
                <td><a class="btn btn-primary btn-sm" href="/accounts/delete/${account.id}">delete</a></td>
            </tr>
        </#foreach>
        </tbody>
    </table>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseAddAccount" role="button" aria-expanded="false"
       aria-controls="collapseAddAccount">
        Add Account
    </a>
    <div class="collapse <#if account??>show</#if>" id="collapseAddAccount">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')}"
                           value="<#if account??>${account.name!}</#if>" name="name" placeholder="Name"/>
                    <#if nameError??>
                        <div class="invalid-feedback">
                            ${nameError!}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control ${(user_idError??)?string('is-invalid', '')}"
                           value="<#if account??>${account.user_id!}</#if>" name="user_id" placeholder="User_id"/>
                    <#if user_idError??>
                        <div class="invalid-feedback">
                            ${user_idError!}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control ${(access_tokenError??)?string('is-invalid', '')}"
                           value="<#if account??>${account.access_token!}</#if>" name="access_token" placeholder="Access_token"/>
                    <#if access_tokenError??>
                        <div class="invalid-feedback">
                            ${access_tokenError!}
                        </div>
                    </#if>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
            </form>
        </div>
    </div>
</@c.page>