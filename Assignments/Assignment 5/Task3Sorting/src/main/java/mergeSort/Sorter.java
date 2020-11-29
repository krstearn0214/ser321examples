package mergeSort;

import java.util.PriorityQueue;

import org.json.JSONObject;

public class Sorter extends Node {
  private PriorityQueue<Integer> sorted = new PriorityQueue<Integer>();
  
  public Sorter(int port) {
    super (port);
  }
  //removed synchronized as not relevant, did not edit other methods as never called
  public synchronized JSONObject init(JSONObject object) {
    /*
    sorted.clear();
    for (var val : object.getJSONArray("data")) {
      sorted.add((Integer) val);
    }
    */
    JSONObject jDone = new JSONObject();
    jDone = MergeSorter.mergeSortJSON(object);
    //System.out.println(port.toString() + "sorted" + jDone.toString());
    return MergeSorter.mergeSortJSON(object);
  }

  public synchronized JSONObject peek(JSONObject object) {
    object.put("response", true);
    if (sorted.size() > 0) {
      object.put("hasValue", true);
      object.put("value", sorted.peek());
    } else {
      object.put("hasValue", false);
    }
    return object;
  }

  public synchronized JSONObject remove(JSONObject object) {
    object.put("response", true);
    if (sorted.size() > 0) {
      object.put("hasValue", true);
      object.put("value", sorted.remove());
    } else {
      object.put("hasValue", false);
    }
    return object;
  }

  public JSONObject error(String error) {
    JSONObject ret = new JSONObject();
    ret.put("error", error);
    return ret;
  }
}