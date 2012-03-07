package russell.john.shared.action;

import com.gwtplatform.dispatch.shared.Action;

public class SetSettingsAction implements Action<SetSettingsResult> { 

  java.lang.String fbId;
  java.lang.String comment;
  java.util.ArrayList<java.lang.String> friends;

  public SetSettingsAction(java.lang.String fbId, java.lang.String comment, java.util.ArrayList<java.lang.String> friends) {
    this.fbId = fbId;
    this.comment = comment;
    this.friends = friends;
  }

  protected SetSettingsAction() {
    // Possibly for serialization.
  }

  public java.lang.String getFbId() {
    return fbId;
  }

  public java.lang.String getComment() {
    return comment;
  }

  public java.util.ArrayList<java.lang.String> getFriends() {
    return friends;
  }

  @Override
  public String getServiceName() {
    return "dispatch/";
  }

  @Override
  public boolean isSecured() {
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    SetSettingsAction other = (SetSettingsAction) obj;
    if (fbId == null) {
      if (other.fbId != null)
        return false;
    } else if (!fbId.equals(other.fbId))
      return false;
    if (comment == null) {
      if (other.comment != null)
        return false;
    } else if (!comment.equals(other.comment))
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
    hashCode = (hashCode * 37) + (fbId == null ? 1 : fbId.hashCode());
    hashCode = (hashCode * 37) + (comment == null ? 1 : comment.hashCode());
    hashCode = (hashCode * 37) + (friends == null ? 1 : friends.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "SetSettingsAction["
                 + fbId
                 + ","
                 + comment
                 + ","
                 + friends
    + "]";
  }
}
