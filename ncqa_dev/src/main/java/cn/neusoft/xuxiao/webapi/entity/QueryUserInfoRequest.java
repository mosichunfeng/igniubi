package cn.neusoft.xuxiao.webapi.entity;

public class QueryUserInfoRequest
{
  public boolean equals(Object o)
  {
    if (o == this) return true; if (!(o instanceof QueryUserInfoRequest)) return false; QueryUserInfoRequest other = (QueryUserInfoRequest)o; return other.canEqual(this); } 
  protected boolean canEqual(Object other) { return other instanceof QueryUserInfoRequest; } 
  public int hashCode() { return 1; } 
  public String toString() { return "QueryUserInfoRequest()";
  }
}