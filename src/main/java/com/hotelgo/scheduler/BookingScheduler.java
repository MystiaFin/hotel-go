package com.hotelgo.scheduler;

import com.hotelgo.repository.BookingRepository;

import java.util.Timer;
import java.util.TimerTask;

public class BookingScheduler {
    private static final BookingRepository bookingRepo = new BookingRepository();

    public static void start() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int expiredCount = bookingRepo.updateExpirePendingBookings();
                if (expiredCount > 0) {
                    System.out.println("Expired " + expiredCount + " pending bookings");
                }
            }
        }, 0, 60 * 1000); // cek tiap 1 menit
    }
}
