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
}
