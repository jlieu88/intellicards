package persistence;

import org.json.JSONObject;

// Effects: returns this as JSON object
public interface Writable {
    JSONObject toJson();
}
