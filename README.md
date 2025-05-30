# Final Project UI & API Test Automation

This project is a test automation framework for REST API and Web UI using Java, Rest-Assured, Selenium, and Cucumber. The framework aims to simplify automated testing of web applications and API services.

---

## 🧰 Tools & Libraries Used

| Tool            | Description                                 |
|-----------------|---------------------------------------------|
| Java            | Programming language                        |
| Gradle          | Build automation tool                       |
| Cucumber        | BDD framework for writing test scenarios    |
| Selenium        | Web UI automation framework                 |
| Rest Assured    | API testing framework                       |
| JSON Schema Validator | API response validation              |
| GitHub Actions  | CI/CD for test automation                   |

---

## Struktur Proyek

final-project-ui-api/
├── .github/workflows/ # GitHub Actions workflow untuk API dan UI test
│ ├── api-test.yml
│ └── ui-test.yml
├── build.gradle # Build script Gradle
├── reports/ # Folder hasil laporan test
│ ├── cucumber-reports.html # Laporan HTML dari Cucumber
│ ├── cucumber-reports.json # Laporan JSON dari Cucumber
│ ├── cucumber-reports/
│ │ ├── timeline/ # Timeline report dari Cucumber
│ │ ├── web-test-report.xml # Laporan JUnit XML
│ │ └── rerun.txt # File rerun test
├── screenshots/ # Folder screenshot hasil test UI
├── src/
│ ├── main/java/org/anna/ # Main class (jika ada)
│ └── test/
│ ├── java/
│ │ ├── helper/ # Utility, endpoint, model, dan helper lainnya
│ │ ├── pages/ # Page Object Model untuk UI test
│ │ ├── runner/ # Test runner Cucumber
│ │ └── stepDefinitions/ # Step definitions untuk Cucumber
│ └── resources/
│ └── features/ # File fitur Cucumber (.feature)
│ ├── api.feature
│ └── webui.feature
├── target/ # Build output dan screenshot hasil test UI
└── README.md # Dokumentasi proyek ini

---

## 🌐 Web UI Target

Website used for UI testing:  
🔗 https://www.demoblaze.com/

## 📡 API Target

Public API used for API testing:  
🔗 https://dummyapi.io/

📄 Run Feature Files Individually
You can also run specific feature files via IDE (IntelliJ) or command line using tags:

`@api` — Run API tests

`@web` — Run UI tests


## 🧪 How to Run

### API Tests
```bash
./gradlew apiTest
```

### Web UI Tests
```bash
./gradlew webTest
```

📈 Test Reports
Test results will be generated in `reports/`

HTML and JSON reports are available after execution

UI screenshots (on failure) will be stored in target/screenshots/

🔁 GitHub Actions
This project is fully integrated with GitHub Actions:

`api-test.yml`: Triggers API tests on Pull Request or manual trigger

`ui-test.yml`: Triggers UI tests on Pull Request or manual trigger

You can trigger workflows manually from the GitHub Actions tab.

📌 Tags Used in Feature Files
| Tag    | Description          |
| ------ | -------------------- |
| `@api` | API test scenario    |
| `@web` | Web UI test scenario |

📚 Folder Details

| Folder/File             | Description                              |
| ----------------------- | ---------------------------------------- |
| `features/`             | Contains Gherkin `.feature` files        |
| `stepDefinitions/`      | Step implementation for Cucumber steps   |
| `pages/`                | Page Object Model classes for UI testing |
| `helper/JSONSchemaData` | JSON Schemas for API validation          |
| `Endpoint.java`         | Centralized API endpoints                |
| `WebDriverFactory.java` | WebDriver initialization for Selenium    |
| `TestRunner.java`       | Runner class for Cucumber tests          |

👤 Author
Project created as part of Automation Testing final assignment.
Maintained by: @ananurbaiti

