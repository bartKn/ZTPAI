package pl.bartkn.ztpai.model.dto.response.split;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SimpleSplitData {
    private Long splitId;
    private boolean finished;
}
