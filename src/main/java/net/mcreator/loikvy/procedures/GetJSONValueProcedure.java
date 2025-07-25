package net.mcreator.loikvy.procedures;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

public class GetJSONValueProcedure {

    /**
     * Gets a JSON value using dot notation path
     * @param jsonString The JSON string to parse (handles escaped JSON)
     * @param path The path to the value (e.g., "key1.key2" or "key1")
     * @return The value as a string, or null if not found
     */
    public static String execute(String jsonString, String path) {
        try {
            // Clean up the JSON string - remove escaped quotes if present
            String cleanJson = cleanJsonString(jsonString);
            
            // Parse the JSON string - using deprecated method for compatibility
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(cleanJson);
            
            if (!jsonElement.isJsonObject()) {
                return null;
            }
            
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            
            // Split the path by dots
            String[] keys = path.split("\\.");
            
            // Navigate through the JSON object
            JsonElement current = jsonObject;
            
            for (String key : keys) {
                if (current == null || !current.isJsonObject()) {
                    return null;
                }
                
                current = current.getAsJsonObject().get(key);
                
                if (current == null) {
                    return null;
                }
            }
            
            // Return the value as string (without quotes for primitives)
            if (current.isJsonPrimitive()) {
                return current.getAsString();
            } else if (current.isJsonObject()) {
                return current.toString();
            } else if (current.isJsonArray()) {
                return current.toString();
            }
            
            return null;
            
        } catch (JsonSyntaxException e) {
            // Invalid JSON
            return null;
        } catch (Exception e) {
            // Any other error
            return null;
        }
    }
    
    /**
     * Cleans up JSON string by handling escaped quotes and other common issues
     */
    private static String cleanJsonString(String jsonString) {
        if (jsonString == null) {
            return null;
        }
        
        // Trim whitespace
        String cleaned = jsonString.trim();
        
        // If the string starts and ends with quotes, and contains escaped quotes inside,
        // it might be a double-encoded JSON string
        if (cleaned.startsWith("\"") && cleaned.endsWith("\"")) {
            // Remove outer quotes
            cleaned = cleaned.substring(1, cleaned.length() - 1);
        }
        
        // Replace escaped quotes with regular quotes
        cleaned = cleaned.replace("\\\"", "\"");
        
        // Replace escaped backslashes
        cleaned = cleaned.replace("\\\\", "\\");
        
        return cleaned;
    }
    
    /**
     * Helper method to check if JSON is valid (after cleaning)
     */
    public static boolean isValidJson(String jsonString) {
        try {
            String cleanJson = cleanJsonString(jsonString);
            JsonParser parser = new JsonParser();
            parser.parse(cleanJson);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Helper method to get JSON object (returns full object as string)
     */
    public static String getJsonObject(String jsonString, String path) {
        try {
            String cleanJson = cleanJsonString(jsonString);
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(cleanJson);
            
            if (!jsonElement.isJsonObject()) {
                return null;
            }
            
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String[] keys = path.split("\\.");
            JsonElement current = jsonObject;
            
            for (String key : keys) {
                if (current == null || !current.isJsonObject()) {
                    return null;
                }
                current = current.getAsJsonObject().get(key);
                if (current == null) {
                    return null;
                }
            }
            
            return current.toString();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Debug method to see what the cleaned JSON looks like
     */
    public static String debugCleanJson(String jsonString) {
        return cleanJsonString(jsonString);
    }
}