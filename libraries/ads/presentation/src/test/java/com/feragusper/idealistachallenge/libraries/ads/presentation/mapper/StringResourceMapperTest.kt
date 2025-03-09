package com.feragusper.idealistachallenge.libraries.ads.presentation.mapper

import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.presentation.R
import com.feragusper.idealistachallenge.libraries.design.StringResource
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class StringResourceMapperTest {

    @Nested
    @DisplayName("GIVEN a property type")
    inner class PropertyTypeMappingTests {

        @Test
        fun `WHEN mapping FLAT THEN correct StringResource is returned`() {
            // WHEN
            val result = StringResourceMapper.mapPropertyType(Ad.PropertyType.FLAT)

            // THEN
            (result as StringResource.Resource).resId shouldBe R.string.ad_apartment
        }
    }

    @Nested
    @DisplayName("GIVEN an operation type")
    inner class OperationTypeMappingTests {

        @Test
        fun `WHEN mapping SALE THEN correct StringResource is returned`() {
            // WHEN
            val result = StringResourceMapper.mapOperationType(Ad.OperationType.SALE)

            // THEN
            (result as StringResource.Resource).resId shouldBe R.string.ad_sale
        }

        @Test
        fun `WHEN mapping RENT THEN correct StringResource is returned`() {
            // WHEN
            val result = StringResourceMapper.mapOperationType(Ad.OperationType.RENT)

            // THEN
            (result as StringResource.Resource).resId shouldBe R.string.ad_rent
        }
    }

    @Nested
    @DisplayName("GIVEN a property status")
    inner class PropertyStatusMappingTests {

        @Test
        fun `WHEN mapping RENEWED THEN correct StringResource is returned`() {
            // WHEN
            val result = StringResourceMapper.mapPropertyStatus(Ad.PropertyStatus.RENEWED)

            // THEN
            (result as StringResource.Resource).resId shouldBe R.string.ad_renewed
        }
    }
}
