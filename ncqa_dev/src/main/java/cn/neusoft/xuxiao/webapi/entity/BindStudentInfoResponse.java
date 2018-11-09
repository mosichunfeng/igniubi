package cn.neusoft.xuxiao.webapi.entity;

public class BindStudentInfoResponse
{
  private int id;
  private String student_id;
  private String student_name;

  public int getId()
  {
    return this.id; } 
  public String getStudent_id() { return this.student_id; } 
  public String getStudent_name() { return this.student_name;
  }

  public void setId(int id)
  {
    this.id = id; } 
  public void setStudent_id(String student_id) { this.student_id = student_id; } 
  public void setStudent_name(String student_name) { this.student_name = student_name; } 
  public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof BindStudentInfoResponse)) return false; BindStudentInfoResponse other = (BindStudentInfoResponse)o; if (!other.canEqual(this)) return false; if (getId() != other.getId()) return false; Object this$student_id = getStudent_id(); Object other$student_id = other.getStudent_id(); if (this$student_id == null ? other$student_id != null : !this$student_id.equals(other$student_id)) return false; Object this$student_name = getStudent_name(); Object other$student_name = other.getStudent_name(); return this$student_name == null ? other$student_name == null : this$student_name.equals(other$student_name); } 
  protected boolean canEqual(Object other) { return other instanceof BindStudentInfoResponse; } 
  public int hashCode() { int PRIME = 59; int result = 1; result = result * 59 + getId(); Object $student_id = getStudent_id(); result = result * 59 + ($student_id == null ? 43 : $student_id.hashCode()); Object $student_name = getStudent_name(); return result * 59 + ($student_name == null ? 43 : $student_name.hashCode()); } 
  public String toString() { return "BindStudentInfoResponse(id=" + getId() + ", student_id=" + getStudent_id() + ", student_name=" + getStudent_name() + ")";
  }
}