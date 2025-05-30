package tests;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BoardPage;

public class BoardTest extends BaseTest {
    @Test
    public void createAndDeleteListsTest() {
        driver.get("http://localhost:3000/");
        BoardPage board = new BoardPage(driver);

        // Open and create a board
        board.openBoard();
        board.createBoard("Test Board 1");

        // Create lists and verify
        board.createLists("First List");
        board.createLists("Second List");
        Assert.assertEquals(board.getListsCount(), 2, "Two lists should be created");

        // Delete lists
        board.deleteAllLists();


        }

    }
