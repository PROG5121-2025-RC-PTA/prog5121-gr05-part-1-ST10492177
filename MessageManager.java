
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.util.ArrayList;

public class MessageManager {
    ArrayList<Message> sent = new ArrayList<>();
    ArrayList<Message> stored = new ArrayList<>();
    ArrayList<Message> disregarded = new ArrayList<>();

    /**
     *
     * @param message
     * @param action
     */
    public void handleMessage(Message message, String action) {
        switch (action) {
            case "Send":
                message.setStatus("Sent");
                sent.add(message);
                break;
            case "Store":
                message.setStatus("Stored");
                stored.add(message);
                storeMessageToJSON(message);
                break;
            case "Disregard":
                message.setStatus("Disregarded");
                disregarded.add(message);
                break;
        }
    }

    private void storeMessageToJSON(Message message) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("id", message.id);
            obj.put("recipient", message.recipient);
            obj.put("text", message.text);
            obj.put("hash", message.createMessageHash());

            JSONArray list = new JSONArray();
            list.add(obj);

            FileWriter file = new FileWriter("storedMessages.json", true);
            file.write(list.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTotalSent() {
        return sent.size();
    }

    
   
    }

    

