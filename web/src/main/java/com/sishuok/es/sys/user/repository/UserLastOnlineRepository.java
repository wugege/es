/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.repository;

import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserLastOnline;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface UserLastOnlineRepository extends BaseRepository<UserLastOnline, Long> {

    UserLastOnline findByUserId(Long userId);
}
