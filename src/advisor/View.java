package advisor;

import java.util.ArrayList;
import java.util.List;

public class View {
    private List<String> outputList;
    private final int entriesPerPageNum;
    private int currentPage;
    private int pagesNum;

    public View(int entriesPerPageNum) {
        outputList = new ArrayList<>();
        this.entriesPerPageNum = entriesPerPageNum;
        currentPage = 0;
        pagesNum = 0;
    }

    public void update(List<String> outputList) {
        this.outputList = outputList;
        if (outputList == null) {
            return;
        }
        pagesNum = (int) Math.ceil(outputList.size() / (double) entriesPerPageNum);
        System.out.println(outputList.size() + " " + pagesNum);
        currentPage = 0;
    }

    public List<String> getCurrentPage() {
        if (outputList == null) {
            return null;
        }

        int currentToIndex = entriesPerPageNum * currentPage + entriesPerPageNum;
        if (currentToIndex > outputList.size()) {
            currentToIndex = outputList.size();
        }

        List<String> output = new ArrayList<>(outputList.subList(entriesPerPageNum * currentPage, currentToIndex));
        output.add("---PAGE " + (currentPage + 1) + " OF " + pagesNum + "---");

        return output;
    }

    public List<String> getPrevPage() {
        if (currentPage == 0) {
            return new ArrayList<>(List.of("No more pages."));
        }

        currentPage--;
        return getCurrentPage();
    }

    public List<String> getNextPage() {
        if (currentPage + 1 == pagesNum) {
            return new ArrayList<>(List.of("No more pages."));
        }

        currentPage++;
        return getCurrentPage();
    }
}
