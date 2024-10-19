package com.gts.backcommons.ssi;

import com.gts.backcommons.models.CommonUser;

import java.util.List;

@SuppressWarnings("java:S1452")
public interface SsiConfiguration {

    List<? extends CommonUser> getAllUsers();
}
