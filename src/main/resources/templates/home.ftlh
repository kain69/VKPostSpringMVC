<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div class="row text-center">
        <div class="col-sm">
            <form method="post" action="/set-account">
                <div class="row">
                    <div class="col-12">
                        <div class="form-row">
                            <div class="col-9">
                                <select name="account" class="form-control">
                                    <#list accounts as account>
                                        <option name="account" <#if account.selected=true>selected</#if>>${account.name}</option>
                                    </#list>
                                </select>
                            </div>
                            <div class="col-3">
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-primary">Set</button>
                                <a class="btn btn-primary" href="/accounts/index">Edit</a>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div class="row col-12 mt-2">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <a href="/post-processing/index" class="btn btn-primary btn-block">Post processing</a>
            </div>
            <form method="post" action="/posts/update">
                <div class="row col-12 mt-2 form-group">
                    <textarea class="form-control" id="textPost" name="textPost"
                              rows="7" style="resize: none"
                              placeholder="Text of post" required><#if post??>${post.text}</#if></textarea>
                </div>
                <div class="row col-12 mt-2">
                    <label for="password" class="sr-only">photo_id</label>
                    <input type="text" id="photoId" name="photoId" class="form-control"
                           placeholder="photo_id" value="<#if post??>${post.photo_id}</#if>" required>
                </div>
                <div class="row col-12 mt-2">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>

        </div>
        <div class="col-sm">
            <table class="table table-bordered table-sm">
                <thead class="">
                <tr>
                    <th style="width: 40%">name</th>
                    <th style="width: 40%">group_id</th>
                    <th style="width: 20%">action</th>
                </tr>
                </thead>
                <tbody>
                <#foreach group in groups>
                    <tr>
                        <td>${group.name}</td>
                        <td>${group.group_id}</td>
                        <td><a class="btn btn-primary btn-sm" href="/groups/delete/${group.id}">delete</a></td>
                    </tr>
                </#foreach>
                </tbody>
            </table>
            <a class="btn btn-primary" data-toggle="collapse" href="#collapseAddGroup" role="button"
               aria-expanded="false"
               aria-controls="collapseAddGroup">
                Add Group
            </a>
            <div class="collapse <#if group??>show</#if>" id="collapseAddGroup">
                <div class="form-group mt-3">
                    <form method="post" action="/groups/add">
                        <div class="form-group">
                            <input type="text" class="form-control"
                                   value="<#if group??>${group.name!}</#if>" name="name" placeholder="name" required/>
                            <#if nameError??>
                                <div class="invalid-feedback">
                                    ${nameError!}
                                </div>
                            </#if>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control ${(group_idError??)?string('is-invalid', '')}"
                                   value="<#if group??>${group.user_id!}</#if>" name="group_id"
                                   placeholder="group_id" required/>
                            <#if group_idError??>
                                <div class="invalid-feedback">
                                    ${group_idError!}
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
        </div>
    </div>
</@c.page>
