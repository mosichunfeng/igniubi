package cn.neusoft.xuxiao.webapi.entity;

public class QueryUserAnserHistoryRequest
{
  private int user_id;

  public int getUser_id()
  {
    return this.user_id;
  }

  public void setUser_id(int user_id)
  {
    this.user_id = user_id; } 
  public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof QueryUserAnserHistoryRequest)) return false; QueryUserAnserHistoryRequest other = (QueryUserAnserHistoryRequest)o; if (!other.canEqual(this)) return false; return getUser_id() == other.getUser_id(); } 
  protected boolean canEqual(Object other) { return other instanceof QueryUserAnserHistoryRequest; } 
  public int hashCode() { int PRIME = 59; int result = 1; return result * 59 + getUser_id(); } 
  public String toString() { return "QueryUserAnserHistoryRequest(user_id=" + getUser_id() + ")";
  }
}