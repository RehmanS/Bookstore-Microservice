package com.bookstore.libraryservice.mapper;

import com.bookstore.libraryservice.dto.LibraryDto;
import com.bookstore.libraryservice.entity.Library;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * ReportingPolicy.IGNORE, dönüşüm sırasında eşleşmeyen alanların görmezden gelinmesini ifade eder.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LibraryMapper {

    /**
     * source: Library    target: LibraryDto
     * @Mapping(target = "userBookList", source = "userBooks")   sourcedeki ve targetdeki field adlari ferqli olarsa
     */
    @Mapping(target = "userBookList", ignore = true)     // targetde userBookList adli field var amma sourcede yoxdu
    LibraryDto convertLibraryToLibraryDto(Library library);
}
