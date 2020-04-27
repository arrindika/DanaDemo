import demo.EmployeeRequest;
import demo.EmployeerResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class Employee {
    @Test
    public void getEmployee(){
        Response response = RestAssured
                .given()
                .baseUri("http://dummy.restapiexample.com")
                .basePath("/api")
                .log()
                .all()
                .header("Content-type", "application/json")
                .header("Accept", "*/*")
                .get("/v1/employees");

        response.getBody().prettyPrint();
        System.out.println(response.getStatusCode());
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertThat("lama", response.getTime(), Matchers.lessThan(3000L));

        Assert.assertEquals("success",response.path("status"));
        Assert.assertEquals("Tiger Nixon", response.path("data[0].employee_name"));

        EmployeerResponse employeerResponse = response.as(EmployeerResponse.class);
        System.out.println(employeerResponse.getStatus());
        System.out.println(employeerResponse.getData().get(0).getEmployeeName());
    }

    @Test
    public void createEmployee(){
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("Brow");
        employeeRequest.setAge("17");
        employeeRequest.setSalary("12000");

        Response response = RestAssured
                .given()
                .baseUri("http://dummy.restapiexample.com")
                .basePath("/api")
                .log()
                .all()
                .header("Content-type", "application/json")
                .header("Accept", "*/*")
                .body(employeeRequest)
                .post("/v1/create");

        response.getBody().prettyPrint();
    }

}
