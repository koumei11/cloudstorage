package com.storage.jwdnd.cloud.cloudstorage.mapper;

import com.storage.jwdnd.cloud.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getCredentials();

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid)" +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET " +
            "url = #{url}, " +
            "username = #{username}, " +
            "password = #{password}" +
            "WHERE credentialid = #{credentialId}")
    int update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    int delete(Integer credentialId);
}