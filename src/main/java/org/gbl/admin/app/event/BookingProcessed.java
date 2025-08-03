package org.gbl.admin.app.event;

import org.gbl.kernel.application.Event;

public sealed interface BookingProcessed extends Event permits BookingConfirmed, BookingFailed {
}
