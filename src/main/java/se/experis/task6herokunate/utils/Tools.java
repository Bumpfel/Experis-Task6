package se.experis.task6herokunate.utils;

public class Tools {
    /**
   * Update the fields of original object based on newObject (yay reflection)
   * @param original
   * @param newObject
   * @throws RuntimeException if objects are of different types
   */
  public static boolean updateFields(Object original, Object newObject, boolean updateNullFields) throws RuntimeException {
    if(original.getClass() != newObject.getClass()) {
      throw new RuntimeException("Objects are not of the same type");
    }

    var fields = original.getClass().getDeclaredFields();
    for(var field : fields) {
      try {
        var fieldValue = field.get(newObject);
        if(updateNullFields || fieldValue != null) {
          field.set(original, fieldValue);
        }          
      } catch(Exception e) {
        System.err.println("Error");
        return false;
      }
    }

    return true;
  }
}
