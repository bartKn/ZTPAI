package pl.bartkn.ztpai.model.mapper;

import org.mapstruct.Mapper;
import pl.bartkn.ztpai.model.dto.response.split.ISimpleSplitData;
import pl.bartkn.ztpai.model.dto.response.split.ISplitData;
import pl.bartkn.ztpai.model.dto.response.split.SimpleSplitData;
import pl.bartkn.ztpai.model.dto.response.split.SplitData;

@Mapper(componentModel = "spring")
public interface SplitDataMapper {
    SplitData map(ISplitData splitInfo);
    SimpleSplitData mapSimple(ISimpleSplitData splitData);
}
