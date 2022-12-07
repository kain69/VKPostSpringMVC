<#macro login path>
    <form class="form-signin text-center" name="f" method="post" action="/process_login">
        <img class="mb-4" src="/img/vkapi.png" alt="" width="auto" height="100">
        <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <label for="username" class="sr-only">Login</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" name="remember-me"> Remember me
            </label>
        </div>
        <button type="submit" class="btn btn-lg btn-primary btn-block">Sign in</button>
<#--        <h6 class="mt-2"><a href="/registration" class="">or create new account</a></h6>-->
        <p class="mt-5 mb-3 text-muted">&copy; 2022-2023</p>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Sign Out">
    </form>
</#macro>