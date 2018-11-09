package cn.neusoft.xuxiao.webapi.entity;

public class BaseResponse
{
  private int code;
  private String remark;

  public int getCode()
  {
    return this.code;
  }

  public String getRemark()
  {
    return this.remark;
  }

  public void setCode(int code)
  {
    this.code = code; } 
  public void setRemark(String remark) { this.remark = remark; } 
  public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof BaseResponse)) return false; BaseResponse other = (BaseResponse)o; if (!other.canEqual(this)) return false; if (getCode() != other.getCode()) return false; Object this$remark = getRemark(); Object other$remark = other.getRemark(); return this$remark == null ? other$remark == null : this$remark.equals(other$remark); } 
  protected boolean canEqual(Object other) { return other instanceof BaseResponse; } 
  public int hashCode() { int PRIME = 59; int result = 1; result = result * 59 + getCode(); Object $remark = getRemark(); return result * 59 + ($remark == null ? 43 : $remark.hashCode()); } 
  public String toString() { return "BaseResponse(code=" + getCode() + ", remark=" + getRemark() + ")";
  }
}