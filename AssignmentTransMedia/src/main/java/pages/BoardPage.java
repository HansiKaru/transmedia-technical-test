package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.TimeoutException;

public class BoardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public BoardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators for board-related elements
    private By boardItem = By.cssSelector("div[data-cy='create-board']");
    private By boardNameInput = By.cssSelector("[data-cy='new-board-input']");
    private By createBoardButton = By.cssSelector("[data-cy='new-board-create']");
    private By addAListButton =  By.cssSelector("[data-cy='create-list']");
    private By listNameInput =  By.cssSelector("[data-cy='add-list-input']");
    private By addListButton = By.xpath("//button[normalize-space(text())='Add list']");
    private By listActions =  By.cssSelector("[data-cy='list-options']");
    private By deleteListButtons = By.cssSelector("[data-cy='delete-list']");

    public void openBoard() {
        driver.findElement(boardItem).click();

    }

    public void createBoard(String name) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(boardNameInput));
        input.sendKeys(name);
        input.sendKeys(Keys.ENTER);
    }

    public void createLists(String... texts) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String text : texts) {
            // Locate and interact with the input field
            WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(listNameInput));
            inputField.clear();
            inputField.sendKeys(text);

            // Locate and click the submit button
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(addListButton));
            submitButton.click();

            // Click "Add a list" button to prepare for the next entry
            if (text != texts[texts.length - 1]) {
                WebElement addNewButton = wait.until(ExpectedConditions.elementToBeClickable(addAListButton));
                addNewButton.click();
            }
        }
        try {
            // Add a small pause to ensure list creation is complete
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void deleteAllLists(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement actionButton = wait.until(ExpectedConditions.elementToBeClickable(listActions));
        actionButton.click();

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(deleteListButtons));
        deleteButton.click();

        // Wait for deletion to complete
        wait.until(ExpectedConditions.invisibilityOfElementLocated(deleteListButtons));

    }

    public int getListsCount() {
        return driver.findElements(By.cssSelector("[data-cy='list']")).size();
    }


}