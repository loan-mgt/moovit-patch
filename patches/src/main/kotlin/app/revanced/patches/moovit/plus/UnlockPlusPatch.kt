package app.revanced.patches.moovit.plus

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference

@Suppress("unused")
val unlockPlusPatch = bytecodePatch(
    name = "Unlock Moovit+",
    description = "Unlocks the Moovit+ extras without paying, like the extra sort and time-of-travel " +
        "options and compare-on-map, and stops the upgrade-to-Moovit+ popups that nag you on app open. " +
        "Things Moovit runs on its servers, like transit ticketing, still need the real subscription. " +
        "Pair this with Remove ads for the ad-free part of Moovit+.",
) {
    compatibleWith("com.tranzmate"("5.194.0.1785"))

    apply {
        // Find the subscription manager class by the "subscribed_skus" string it contains,
        // then locate the ()Z subscription check method within that class.
        val subscriptionClass = classDefs.firstOrNull { classDef ->
            classDef.methods.any { method ->
                method.implementation?.instructions?.any { instruction ->
                    ((instruction as? ReferenceInstruction)?.reference as? StringReference)
                        ?.string == "subscribed_skus"
                } == true
            }
        } ?: throw PatchException(
            "Moovit: subscription manager class not found. The obfuscation layout changed.",
        )
        val subscriptionCheck = proxy(subscriptionClass).mutableClass.methods.firstOrNull {
            AccessFlags.PUBLIC.isSet(it.accessFlags) &&
                AccessFlags.FINAL.isSet(it.accessFlags) &&
                it.returnType == "Z" &&
                it.parameterTypes.isEmpty()
        } ?: throw PatchException(
            "Moovit: subscription check ()Z not found. The obfuscation layout changed.",
        )
        subscriptionCheck.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """,
        )

        // BlockPaywallActivity upsell interstitial.
        val paywallDescriptor = "Lcom/moovit/app/plus/paywall/BlockPaywallActivity;"
        val paywallClassDef = classDefs.firstOrNull { it.type == paywallDescriptor }
            ?: throw PatchException(
                "Moovit: BlockPaywallActivity not found. The upsell-interstitial layout changed.",
            )
        val paywallActivity = proxy(paywallClassDef).mutableClass
        val paywallOnReady = paywallActivity.methods.singleOrNull {
            it.name == "onReady" && it.returnType == "V" &&
                it.parameterTypes.singleOrNull()?.toString() == "Landroid/os/Bundle;"
        } ?: throw PatchException("Moovit: onReady(Bundle) not found in BlockPaywallActivity.")
        val paywallSkip = paywallActivity.methods.singleOrNull { method ->
            AccessFlags.PRIVATE.isSet(method.accessFlags) &&
                method.parameterTypes.isEmpty() && method.returnType == "V" &&
                method.implementation?.instructions?.any { instruction ->
                    ((instruction as? ReferenceInstruction)?.reference as? MethodReference)
                        ?.name == "getActivityToStartOnFinish"
                } == true
        } ?: throw PatchException(
            "Moovit: the BlockPaywall skip method was not found. " +
                "The upsell-interstitial layout changed.",
        )
        paywallOnReady.addInstructions(
            0,
            "invoke-direct { p0 }, $paywallDescriptor->${paywallSkip.name}()V\nreturn-void",
        )

        // MoovitPlusOnboardingActivity upsell interstitial.
        val onboardingDescriptor = "Lcom/moovit/app/plus/onboarding/MoovitPlusOnboardingActivity;"
        val onboardingClassDef = classDefs.firstOrNull { it.type == onboardingDescriptor }
            ?: throw PatchException(
                "Moovit: MoovitPlusOnboardingActivity not found. " +
                    "The upsell-interstitial layout changed.",
            )
        val onboardingActivity = proxy(onboardingClassDef).mutableClass
        val onboardingOnReady = onboardingActivity.methods.singleOrNull {
            it.name == "onReady" && it.returnType == "V" &&
                it.parameterTypes.singleOrNull()?.toString() == "Landroid/os/Bundle;"
        } ?: throw PatchException(
            "Moovit: onReady(Bundle) not found in MoovitPlusOnboardingActivity.",
        )
        val onboardingSkip = onboardingActivity.methods.singleOrNull { method ->
            method.parameterTypes.isEmpty() && method.returnType == "V" &&
                method.implementation?.instructions?.any { instruction ->
                    ((instruction as? ReferenceInstruction)?.reference as? StringReference)
                        ?.string == "activity_to_start_on_finish"
                } == true
        } ?: throw PatchException(
            "Moovit: the onboarding skip method was not found. " +
                "The upsell-interstitial layout changed.",
        )
        onboardingOnReady.addInstructions(
            0,
            "invoke-virtual { p0 }, $onboardingDescriptor->${onboardingSkip.name}()V\nreturn-void",
        )
    }
}
