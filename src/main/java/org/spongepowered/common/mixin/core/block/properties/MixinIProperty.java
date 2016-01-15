package org.spongepowered.common.mixin.core.block.properties;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyHelper;
import org.spongepowered.api.block.trait.BlockTrait;
import org.spongepowered.api.util.Functional;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.common.registry.type.BlockTypeRegistryModule;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * This default implements the {@link BlockTrait} methods directly
 * into {@link IProperty} such that regardless of whether a mod extends
 * {@link PropertyHelper} or only implements {@link IProperty}, we can
 * still cast back and forth between the implementation interface and
 * API interface. Of course, this is Java 8 only functionality, but
 * still, as with all other Mixin classes, this mixin class cannot be
 * directly referenced in any manner.
 *
 * @param <T> The type of comparable.
 */
@Mixin(IProperty.class)
@Implements(@Interface(iface = BlockTrait.class, prefix = "trait$"))
public interface MixinIProperty<T extends Comparable<T>> extends IProperty<T> {

    default String trait$getId() {
        return BlockTypeRegistryModule.getInstance().getIdFor(this);
    }

    @Intrinsic
    default String trait$getName() {
        return getName();
    }

    default Collection<T> trait$getPossibleValues() {
        return getAllowedValues();
    }

    @Intrinsic
    default Class<T> trait$getValueClass() {
        return getValueClass();
    }

    default Predicate<T> trait$getPredicate() {
        return Functional.predicateIn(getAllowedValues());
    }
}
