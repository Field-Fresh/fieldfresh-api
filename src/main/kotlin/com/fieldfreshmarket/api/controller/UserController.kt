package com.fieldfreshmarket.api.controller

import com.fieldfreshmarket.api.controller.request.user.UpdateUserRequest
import com.fieldfreshmarket.api.core.Controller
import com.fieldfreshmarket.api.services.UserService
import com.fieldfreshmarket.api.view.UserView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/auth"])
class UserController : Controller() {

    @Autowired
    private lateinit var userService: UserService

    @PatchMapping("/update")
    fun update(@RequestBody request: UpdateUserRequest): UserView =
        UserView(userService.update(grant, request.toData(grant)))
}