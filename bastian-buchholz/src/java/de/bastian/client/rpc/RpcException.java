package de.bastian.client.rpc;

import java.io.Serializable;

public class RpcException extends Exception implements Serializable {

  private String error;

  public RpcException() {
  }

  public RpcException(String error) {
    this.error = error;
  }

  public String getError() {
    return error;
  }

}
