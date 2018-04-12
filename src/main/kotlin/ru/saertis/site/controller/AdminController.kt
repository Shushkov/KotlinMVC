package ru.saertis.site.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by Sergey Shushkov on 11.04.2018.
 * Java Team
 */
@Controller
@RequestMapping("")
class AdminController {

    @RequestMapping("")
    fun index(): String{
        return "index"
    }

    @RequestMapping("/login")
    fun login(): String{
        return "login"
    }
}