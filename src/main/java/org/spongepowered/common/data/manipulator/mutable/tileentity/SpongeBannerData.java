package org.spongepowered.common.data.manipulator.mutable.tileentity;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.tileentity.ImmutableBannerData;
import org.spongepowered.api.data.manipulator.mutable.tileentity.BannerData;
import org.spongepowered.api.data.meta.PatternLayer;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.data.value.mutable.PatternListValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.common.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.common.data.value.mutable.SpongePatternListValue;
import org.spongepowered.common.data.value.mutable.SpongeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpongeBannerData extends AbstractData<BannerData, ImmutableBannerData> implements BannerData {

    private DyeColor base;
    private List<PatternLayer> layers;

    public SpongeBannerData(DyeColor base, List<PatternLayer> layers) {
        super(BannerData.class);
        this.base = checkNotNull(base);
        this.layers = checkNotNull(layers).stream().collect(Collectors.toList());
    }

    public SpongeBannerData() {
        super(BannerData.class);
        this.base = DyeColors.WHITE;
        registerGettersAndSetters();
    }

    public DyeColor getBase() {
        return this.base;
    }

    public void setBase(DyeColor base) {
        this.base = checkNotNull(base, "Null DyeColor!");
    }

    public List<PatternLayer> getLayers() {
        return this.layers.stream().collect(Collectors.toList());
    }

    public void setLayers(List<PatternLayer> layers) {
        this.layers = new ArrayList<>(checkNotNull(layers, "Null pattern layers!"));
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(Keys.BANNER_BASE_COLOR, this::getBase);
        registerFieldSetter(Keys.BANNER_BASE_COLOR, this::setBase);
        registerKeyValue(Keys.BANNER_BASE_COLOR, this::baseColor);

        registerFieldGetter(Keys.BANNER_PATTERNS, this::getLayers);
        registerFieldSetter(Keys.BANNER_PATTERNS, this::setLayers);
        registerKeyValue(Keys.BANNER_PATTERNS, this::patternsList);
    }

    @Override
    public BannerData copy() {
        return null;
    }

    @Override
    public ImmutableBannerData asImmutable() {
        return null;
    }

    @Override
    public int compareTo(BannerData o) {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
            .set(Keys.BANNER_BASE_COLOR.getQuery(), this.base.getId())
            .set(Keys.BANNER_PATTERNS, this.layers);
    }

    @Override
    public Value<DyeColor> baseColor() {
        return new SpongeValue<>(Keys.BANNER_BASE_COLOR, DyeColors.WHITE, this.base);
    }

    @Override
    public PatternListValue patternsList() {
        return new SpongePatternListValue(Keys.BANNER_PATTERNS, this.getLayers());
    }
}