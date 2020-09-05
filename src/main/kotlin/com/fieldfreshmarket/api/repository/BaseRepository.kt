package com.fieldfreshmarket.api.repository

import com.fieldfreshmarket.api.model.BaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<I: BaseModel>: JpaRepository<I, Long>