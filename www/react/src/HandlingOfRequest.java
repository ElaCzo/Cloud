public class HandlingOfRequest {
    /*public static void main(String[] args){
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try{
            engine.eval("print('welcome to javascript execution using java')");
            engine.eval(new FileReader("handleQuery"));
            Invocable invocable.invokeFunction("sumOfTwoNumbers", 10, 20);
        }
        catch(ScriptException | FileNotFoundException | NoSuchMethodException e){
            e.printStackTrace();
        }
    }*/
    //asynchronously retrieve multiple documents
    /*ApiFuture<QuerySnapshot> future =
            db.collection("livres").whereEqualTo("capital", true).get();
    // future.get() blocks on response
    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
for (DocumentSnapshot document : documents) {
        System.out.println(document.getId() + " => " + document.toObject(City.class));
    }*/

    public static String handlingOfRequest(String terms){
        return "livre de "+terms
    }
}