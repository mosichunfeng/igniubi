package cn.neusoft.xuxiao.webapi.entity;

public class GetSessionKeyAndOpenIdResponse extends BaseResponse
{
  private int id;
  private boolean bind_info;

  public int getId()
  {
    return this.id;
  }

  public boolean isBind_info() {
    return this.bind_info;
  }

  public void setId(int id)
  {
    this.id = id; } 
  public void setBind_info(boolean bind_info) { this.bind_info = bind_info; } 
  public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof GetSessionKeyAndOpenIdResponse)) return false; GetSessionKeyAndOpenIdResponse other = (GetSessionKeyAndOpenIdResponse)o; if (!other.canEqual(this)) return false; if (getId() != other.getId()) return false; return isBind_info() == other.isBind_info(); } 
  protected boolean canEqual(Object other) { return other instanceof GetSessionKeyAndOpenIdResponse; } 
  public int hashCode() { int PRIME = 59; int result = 1; result = result * 59 + getId(); return result * 59 + (isBind_info() ? 79 : 97); } 
  public String toString() { return "GetSessionKeyAndOpenIdResponse(id=" + getId() + ", bind_info=" + isBind_info() + ")";
  }
}