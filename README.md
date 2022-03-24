Automation Framework

I created this project as a way to demonstrate my knowledge of Selenium by creating a working framework 
that could be used to test a website for functionality. I have worked as an intern in the Software 
Quality Assurance field and while it was very interesting to me I was more interested in the Automation
side of QA. So, after obtaining a degree I set out to learn the necessary components to be qualified to perform 
automation testing. 

This project uses a dummy site for the tests, since I intend to use this on a resume. This made the most sense 
since the website is not updated which eliminates the need to constantly maintain the framework for potential employers
and eliminates the possibility of an update breaking my code and a potential employer trying it before I could get a chance to fix it.

The framework uses a page object model to simplify and improve the readability of the code. It also features a data.properties
file that contains any needed values for the tests to make it easy to update the values in all tests at once. The file is 
located in the resources package along with any other files that are needed to run the code, like the drivers. This makes it 
so the framework can be simply downloaded with no need for changes or creating any files. It requires Java, Maven, and for it
to ideally be setup on Jenkins, but it could be run without Jenkins.

The TestNG xml file is configured to run the tests in parallel to reduce the execution time. Currently the framework is set up
to be run through Jenkins, but it can also be run through the command prompt using Maven commands. It is set up to accept
Jenkins parameters so you can choose if you would like to run it in head or headless mode and also choose which browser you would like 
to run the tests in, Chrome, Firefox, or Edge. To run it through the command prompt, send the command "mvn test -Dbrowser={desiredbrowser}" 
and replace {desiredbrowser} with your preference. The options for {desiredbrowser} are: "chrome", "chromeheadless", "firefox", 
"firefoxheadless", "edge", and "edgeheadless". In the base class under the resources package I have added comments to the lines that can be 
uncommented/commented if you wish to run it in an IDE. If you run it in an IDE you will also need to change the browser value in the
data.properties file to one of the previously mentioned options.

This seems like a lot to simply run the program, but ideally it is designed to be run in Jenkins. This simplifies the process of running the
tests considerably since you simply need to navigate to Jenkins and select the test, then select your parameters, and select run. It will then 
display and save the results of the test in Jenkins so there is no need to open an IDE to perform testing.

NOTE: One of the tests will fail, this is expected. The website I used for testing displays incorrect results in the search bar for one 
of the search terms.

I hope you enjoy my project!
