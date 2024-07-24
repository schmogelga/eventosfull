package com.schmogel.eventosfull.mock;

import com.schmogel.eventosfull.domain.model.UserInfo;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UserMock {

  public static UserInfo getUserMock(){
    return new UserInfo(
      UUID.randomUUID(),
      "USERNAME",
      "NOME",
      "email@email.com",
      List.of(),
      Set.of()
    );
  }
}
