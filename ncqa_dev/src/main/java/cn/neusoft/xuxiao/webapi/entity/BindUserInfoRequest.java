package cn.neusoft.xuxiao.webapi.entity;

public class BindUserInfoRequest
{
  private int id;
  private String avatarUrl;
  private String nick_name;
  private String city;
  private String country;
  private int gender;

  public int getId()
  {
    return this.id;
  }

  public String getAvatarUrl()
  {
    return this.avatarUrl;
  }

  public String getNick_name()
  {
    return this.nick_name;
  }

  public String getCity()
  {
    return this.city;
  }

  public String getCountry()
  {
    return this.country;
  }

  public int getGender()
  {
    return this.gender;
  }

  public void setId(int id)
  {
    this.id = id; } 
  public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; } 
  public void setNick_name(String nick_name) { this.nick_name = nick_name; } 
  public void setCity(String city) { this.city = city; } 
  public void setCountry(String country) { this.country = country; } 
  public void setGender(int gender) { this.gender = gender; } 
  public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof BindUserInfoRequest)) return false; BindUserInfoRequest other = (BindUserInfoRequest)o; if (!other.canEqual(this)) return false; if (getId() != other.getId()) return false; Object this$avatarUrl = getAvatarUrl(); Object other$avatarUrl = other.getAvatarUrl(); if (this$avatarUrl == null ? other$avatarUrl != null : !this$avatarUrl.equals(other$avatarUrl)) return false; Object this$nick_name = getNick_name(); Object other$nick_name = other.getNick_name(); if (this$nick_name == null ? other$nick_name != null : !this$nick_name.equals(other$nick_name)) return false; Object this$city = getCity(); Object other$city = other.getCity(); if (this$city == null ? other$city != null : !this$city.equals(other$city)) return false; Object this$country = getCountry(); Object other$country = other.getCountry(); if (this$country == null ? other$country != null : !this$country.equals(other$country)) return false; return getGender() == other.getGender(); } 
  protected boolean canEqual(Object other) { return other instanceof BindUserInfoRequest; } 
  public int hashCode() { int PRIME = 59; int result = 1; result = result * 59 + getId(); Object $avatarUrl = getAvatarUrl(); result = result * 59 + ($avatarUrl == null ? 43 : $avatarUrl.hashCode()); Object $nick_name = getNick_name(); result = result * 59 + ($nick_name == null ? 43 : $nick_name.hashCode()); Object $city = getCity(); result = result * 59 + ($city == null ? 43 : $city.hashCode()); Object $country = getCountry(); result = result * 59 + ($country == null ? 43 : $country.hashCode()); return result * 59 + getGender(); } 
  public String toString() { return "BindUserInfoRequest(id=" + getId() + ", avatarUrl=" + getAvatarUrl() + ", nick_name=" + getNick_name() + ", city=" + getCity() + ", country=" + getCountry() + ", gender=" + getGender() + ")";
  }
}