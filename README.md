# GIN

*Gherkin Interpreted Nicely*

Formatting Gherkin files nicely and integrating the test results from Cucumber.

### Prerequisites

* Feature files written in Gherkin. The feature files must end with ".feature" and all have a common parent directory.
* If you want the test results integrated you need to export them from cucumber in json format. 
  For this add the json reporter to your cucumber  test runner:
  
       @CucumberOptions( plugin = { "json:target/cucumber.json" })

### Using the Maven Plugin

Simply add the plugin to your pom:

    <plugin>
      <groupId>com.github.andreashosbach</groupId>
      <artifactId>gin-maven-plugin</artifactId>
      <version>0.0.1</version>
      <executions>
        <execution>
          <configuration>
            <featureFiles>${project.basedir}/src/test/resources/features</featureFiles>
            <outputDirectory>${project.basedir}/target/gin-html</outputDirectory>
            <resultFile>${project.basedir}/target/cucumber.json</resultFile>
            <loglevel>SEVERE</loglevel>
          </configuration>
          <goals>
            <goal>gin</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
 
### Using the Standalone CLI

     java -cf gin-maven-plugin GinCLI [featureFilePath] [testResultFile] [outputDirectory]
     
## Authors

* **Andreas Hosbach** - *Initial work* - [andreashosbach](https://github.com/andreashosbach)

See also the list of [contributors](https://github.com/andreashosbach/gin/contributors) who participated in this project.

## License

This project is licensed under the Apache Version 2.0 License - see the [LICENSE.md](http://www.apache.org/licenses/LICENSE-2.0) for details
GIN uses several open source libraries that are governed by their own licenses.

## Acknowledgments
The Html and Javascript output is based on the work of [Pickles Living Documentation](https://www.picklesdoc.com)

