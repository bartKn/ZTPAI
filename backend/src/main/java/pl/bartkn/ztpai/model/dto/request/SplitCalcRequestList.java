package pl.bartkn.ztpai.model.dto.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SplitCalcRequestList {
    private List<SplitCalcRequest> splitDataList;
}
