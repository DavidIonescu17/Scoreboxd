@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {

    private final MatchRepository matchRepo;

    @GetMapping("/matches")
    public List<Match> allMatches() {
        return matchRepo.findAll();
    }
}
