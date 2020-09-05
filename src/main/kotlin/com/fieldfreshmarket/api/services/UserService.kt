package com.fieldfreshmarket.api.services

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.repository.UsersRepository
import com.fieldfreshmarket.api.data.user.CreateUserData
import com.fieldfreshmarket.api.data.user.UpdateUserData
import com.fieldfreshmarket.api.model.User
import com.fieldfreshmarket.api.usecase.user.CreateUserUsecase
import com.fieldfreshmarket.api.usecase.user.UpdateUserUsecase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
@Component
class UserService {

   @Autowired
   private lateinit var repository: UsersRepository

   @Autowired
   private lateinit var createUserUsecase: CreateUserUsecase

   @Autowired
   private lateinit var updateUserUsecase: UpdateUserUsecase

   fun getProfileByEmail(email: String): User? =
      repository.findByEmail(email)

   fun getProfileBySub(sub: String): User? =
      repository.findByCognitoSub(sub)

   fun create(data: CreateUserData): User {
      return createUserUsecase.execute(data)
   }

   fun update(grant: AccessGrant, data: UpdateUserData): User =
      updateUserUsecase.execute(grant.user, data)
}
