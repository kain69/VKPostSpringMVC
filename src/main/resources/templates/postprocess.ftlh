<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="row">
        <div class="col-12 col-lg-4">
            <form method="post" action="/post-processing/time">
                <div class="form-group">
                    <label class="form-control">Выбранный аккаунт: ${targetAccount!}</label>
                </div>
                <div class="form-group">
                    <input class="form-control time" type="time" id="time1" name="time1"
                           value="${time1!}"/>
                </div>
                <div class="form-group">
                    <input class="form-control time" type="time" id="time2" name="time2"
                           value="${time2!}"/>
                </div>
                <div class="form-group">
                    <input class="form-control time" type="time" id="time3" name="time3"
                           value="${time3!}"/>
                </div>
                <div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-primary">Update</button>
                </div>
            </form>
            <div class="mt-2">
                <form method="post" action="/post-processing/status">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-success" <#if status = true>hidden</#if>>Start</button>
                        <button type="submit" class="btn btn-danger" <#if status = false>hidden</#if>>Stop</button>
                </form>
            </div>
        </div>
    </div>

</@c.page>