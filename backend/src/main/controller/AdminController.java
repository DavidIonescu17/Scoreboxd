@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MatchSyncService syncService;

    @PostMapping("/sync-football")
    public String syncNow() {
        syncService.importYesterday();
        return "OK";
    }
    @GetMapping("/sync-football")
    public String syncGet() {
        return syncNow();   // reuse your POST method
    }

}
