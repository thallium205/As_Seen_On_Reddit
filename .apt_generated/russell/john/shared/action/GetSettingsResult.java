package russell.john.shared.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetSettingsResult implements Result { 

  java.lang.String comment;
  java.util.Date lastCheckedDate;
  java.util.ArrayList<java.lang.String> friends;

  public GetSettingsResult(java.lang.String comment, java.util.Date lastCheckedDate, java.util.ArrayList<java.lang.String> friends) {
    this.comment = comment;
    this.lastCheckedDate = lastCheckedDate;
    this.friends = friends;
  }

  protected GetSettingsResult() {
    // Possibly for serialization.
  }

  public java.lang.String getComment() {
    return comment;
  }

  public java.util.Date getLastCheckedDate() {
    return lastCheckedDate;
  }

  public java.util.ArrayList<java.lang.String> getFriends() {
    return friends;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetSettingsResult other = (GetSettingsResult) obj;
    if (comment == null) {
      if (other.comment != null)
        return false;
    } else if (!comment.equals(other.comment))
      return false;
    if (lastCheckedDate == null) {
      if (other.lastCheckedDate != null)
        return false;
    } else if (!lastCheckedDate.equals(other.lastCheckedDate))
      return false;
    if (friends == null) {
      if (other.friends != null)
        return false;
    } else if (!friends.equals(other.friends))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (comment == null ? 1 : comment.hashCode());
    hashCode = (hashCode * 37) + (lastCheckedDate == null ? 1 : lastCheckedDate.hashCode());
    hashCode = (hashCode * 37) + (friends == null ? 1 : friends.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetSettingsResult["
                 + comment
                 + ","
                 + lastCheckedDate
                 + ","
                 + friends
    + "]";
  }
}
