package cn.neusoft.xuxiao.webapi.entity;

public class AdminLoginResult
{
  private int id;
  private String username;
  private String password;

  public int getId()
  {
    return this.id; } 
  public String getUsername() { return this.username; } 
  public String getPassword() { return this.password;
  }

  public void setId(int id)
  {
    this.id = id; } 
  public void setUsername(String username) { this.username = username; } 
  public void setPassword(String password) { this.password = password; } 
  public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof AdminLoginResult)) return false; AdminLoginResult other = (AdminLoginResult)o; if (!other.canEqual(this)) return false; if (getId() != other.getId()) return false; Object this$username = getUsername(); Object other$username = other.getUsername(); if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false; Object this$password = getPassword(); Object other$password = other.getPassword(); return this$password == null ? other$password == null : this$password.equals(other$password); } 
  protected boolean canEqual(Object other) { return other instanceof AdminLoginResult; } 
  public int hashCode() { int PRIME = 59; int result = 1; result = result * 59 + getId(); Object $username = getUsername(); result = result * 59 + ($username == null ? 43 : $username.hashCode()); Object $password = getPassword(); return result * 59 + ($password == null ? 43 : $password.hashCode()); } 
  public String toString() { return "AdminLoginResult(id=" + getId() + ", username=" + getUsername() + ", password=" + getPassword() + ")";
  }
}