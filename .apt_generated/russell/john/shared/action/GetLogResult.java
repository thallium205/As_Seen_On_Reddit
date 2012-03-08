package russell.john.shared.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetLogResult implements Result { 

  java.util.ArrayList<russell.john.shared.action.GetLogType> results;

  public GetLogResult(java.util.ArrayList<russell.john.shared.action.GetLogType> results) {
    this.results = results;
  }

  protected GetLogResult() {
    // Possibly for serialization.
  }

  public java.util.ArrayList<russell.john.shared.action.GetLogType> getResults() {
    return results;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetLogResult other = (GetLogResult) obj;
    if (results == null) {
      if (other.results != null)
        return false;
    } else if (!results.equals(other.results))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (results == null ? 1 : results.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetLogResult["
                 + results
    + "]";
  }
}
