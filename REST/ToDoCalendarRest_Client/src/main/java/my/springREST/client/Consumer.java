package my.springREST.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import my.springREST.client.request.*;

 

public class Consumer {

    public static void main(String[] args) throws JsonProcessingException {


        // аутентифицироваться
//        Authenticate authenticate = new Authenticate();
//        try {
//            System.out.println(authenticate.getResponse());
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }


//          // еще один способ зарегистрировать нового пользователя
//        Registration registration = new Registration();
//        try {
//            registration.regPerson("user", "1234567", "user@mail.com");
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//


        // зарегистрировать нового пользователя
//        RegPageRequest regPageRequest = new RegPageRequest();
//        try {
//            regPageRequest.post("http://localhost:8080/auth/regPage", "userTest10",
//                    "1234567", "userTest10@mail.com");
//        } catch (Exception e) {
//            System.out.println("nicht arbeiten!!!");
//        }
//

            // показать всех пользователей
//        ShowPerson showPerson = new ShowPerson();
//        try {
//            showPerson.getPerson();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//
//  // удалить пользователя
//        DeleteUser deleteUser = new DeleteUser();
//        try {
//            deleteUser.deleteUser(7, "anton", "1234567");
//
//            //...
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }




//        // внести изменения в пользователя
//        PutUser putUser = new PutUser();
//        try {
//            putUser.putUser(6, "Sider Petrovich", "SidersPetrovich@gmail.com", "ROLE_USER", "anton", "1234567");
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }


                // добавить задачу
//        PostEvent postEvent = new PostEvent();
//        try {
//            postEvent.postEvent("заголовок задачи 8", "описание задачи8 в клиенте", "2022-02-03",
//                    "21:03", "false","Sider Petrovich", "1234567");
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

//  // удалить задачу
//        DeleteEvent deleteEvent = new DeleteEvent();
//        try {
//            deleteEvent.deleteEvent(13, "anton", "1234567");
//
//            //...
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }


//  // search задачу
//        SearchEvent searchEvent = new SearchEvent();
//        try {
//            searchEvent.searchEvent("заг", "Sider Petrovich", "1234567");
//
//            //...
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

//  // ищем задачу по дате "1982-11-22"
//        SearchEventDate searchEventDate = new SearchEventDate();
//        try {
//            searchEventDate.searchEventDate("2022-12-20", "Sider Petrovich", "1234567");
//
//            //...
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }




        
         GetRequest getRequest = new GetRequest();
        try {
            System.out.println("1");
            getRequest.get("http://localhost:8080/person", "anton", "1234567");
            System.out.println("2");
            getRequest.get("http://localhost:8080/testText", "mikhail", "1234567");
            System.out.println("3");
            getRequest.get("http://localhost:8080/hello", "anton", "1234567");
            System.out.println("4");
            getRequest.get("http://localhost:8080/admin", "anton", "1234567");
            System.out.println("5");
            getRequest.get("http://localhost:8080/person/3", "anton", "1234567");
            System.out.println("6 - must be exception!");
            getRequest.get("http://localhost:8080/admin", "user", "1234567");//	"must be exception!!!"
            System.out.println("7");
            getRequest.get("http://localhost:8080/person/1/event", "anton", "1234567");
            System.out.println("8");
            getRequest.get("http://localhost:8080/event", "anton", "1234567");
            System.out.println("9");
            getRequest.get("http://localhost:8080/event/7", "anton", "1234567");
            //...
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }





    }



}
