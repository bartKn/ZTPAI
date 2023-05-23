package pl.bartkn.ztpai.model.mapper;

import org.mapstruct.Mapper;
import pl.bartkn.ztpai.model.dto.response.ISimpleSplitData;
import pl.bartkn.ztpai.model.dto.response.ISplitData;
import pl.bartkn.ztpai.model.dto.response.SimpleSplitData;
import pl.bartkn.ztpai.model.dto.response.SplitData;

@Mapper(componentModel = "spring")
public interface SplitDataMapper {
    SplitData map(ISplitData splitInfo);
    SimpleSplitData mapSimple(ISimpleSplitData splitData);
}