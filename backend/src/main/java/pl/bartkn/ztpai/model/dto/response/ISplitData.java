package pl.bartkn.ztpai.model.dto.response;

import java.math.BigDecimal;

public interface ISplitData {
    String getUsername();
    Long getUserId();
    BigDecimal getContribution();
    Long getSplitId();
    boolean isFinished();
}
