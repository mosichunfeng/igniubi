package cn.neusoft.xuxiao.dao.inf;

public class UserInfo
{
  private int id;
  private String open_id;
  private String account;
  private String student_id;
  private String username;
  private String region;
  private String sex;
  private int age;
  private String head_url;

  public int getId()
  {
    return this.id;
  }

  public String getOpen_id() {
    return this.open_id;
  }

  public String getAccount() {
    return this.account;
  }

  public String getStudent_id() {
    return this.student_id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getRegion() {
    return this.region;
  }

  public String getSex() {
    return this.sex;
  }

  public int getAge() {
    return this.age;
  }

  public String getHead_url() {
    return this.head_url;
  }

  public void setId(int id)
  {
    this.id = id; } 
  public void setOpen_id(String open_id) { this.open_id = open_id; } 
  public void setAccount(String account) { this.account = account; } 
  public void setStudent_id(String student_id) { this.student_id = student_id; } 
  public void setUsername(String username) { this.username = username; } 
  public void setRegion(String region) { this.region = region; } 
  public void setSex(String sex) { this.sex = sex; } 
  public void setAge(int age) { this.age = age; } 
  public void setHead_url(String head_url) { this.head_url = head_url; } 
  public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof UserInfo)) return false; UserInfo other = (UserInfo)o; if (!other.canEqual(this)) return false; if (getId() != other.getId()) return false; Object this$open_id = getOpen_id(); Object other$open_id = other.getOpen_id(); if (this$open_id == null ? other$open_id != null : !this$open_id.equals(other$open_id)) return false; Object this$account = getAccount(); Object other$account = other.getAccount(); if (this$account == null ? other$account != null : !this$account.equals(other$account)) return false; Object this$student_id = getStudent_id(); Object other$student_id = other.getStudent_id(); if (this$student_id == null ? other$student_id != null : !this$student_id.equals(other$student_id)) return false; Object this$username = getUsername(); Object other$username = other.getUsername(); if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false; Object this$region = getRegion(); Object other$region = other.getRegion(); if (this$region == null ? other$region != null : !this$region.equals(other$region)) return false; Object this$sex = getSex(); Object other$sex = other.getSex(); if (this$sex == null ? other$sex != null : !this$sex.equals(other$sex)) return false; if (getAge() != other.getAge()) return false; Object this$head_url = getHead_url(); Object other$head_url = other.getHead_url(); return this$head_url == null ? other$head_url == null : this$head_url.equals(other$head_url); } 
  protected boolean canEqual(Object other) { return other instanceof UserInfo; } 
  public int hashCode() { int PRIME = 59; int result = 1; result = result * 59 + getId(); Object $open_id = getOpen_id(); result = result * 59 + ($open_id == null ? 43 : $open_id.hashCode()); Object $account = getAccount(); result = result * 59 + ($account == null ? 43 : $account.hashCode()); Object $student_id = getStudent_id(); result = result * 59 + ($student_id == null ? 43 : $student_id.hashCode()); Object $username = getUsername(); result = result * 59 + ($username == null ? 43 : $username.hashCode()); Object $region = getRegion(); result = result * 59 + ($region == null ? 43 : $region.hashCode()); Object $sex = getSex(); result = result * 59 + ($sex == null ? 43 : $sex.hashCode()); result = result * 59 + getAge(); Object $head_url = getHead_url(); return result * 59 + ($head_url == null ? 43 : $head_url.hashCode()); } 
  public String toString() { return "UserInfo(id=" + getId() + ", open_id=" + getOpen_id() + ", account=" + getAccount() + ", student_id=" + getStudent_id() + ", username=" + getUsername() + ", region=" + getRegion() + ", sex=" + getSex() + ", age=" + getAge() + ", head_url=" + getHead_url() + ")";
  }
}