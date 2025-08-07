import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { TrendingUp, LogOut } from "lucide-react";
import { useNavigate } from "react-router-dom";

interface NavbarProps {
  user?: { email: string };
  onSignOut: () => void;
}

const Navbar = ({ user, onSignOut }: NavbarProps) => {
  const navigate = useNavigate();

  return (
    <nav className="border-b border-border bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="container mx-auto px-4 h-16 flex items-center justify-between">
        <div className="flex items-center gap-2 cursor-pointer" onClick={() => navigate('/')}>
          <TrendingUp className="h-6 w-6 text-primary" />
          <span className="font-bold text-xl">StockTracker</span>
        </div>
        
        <div className="flex items-center gap-4">
          <Button variant="ghost" onClick={() => navigate('/dashboard')}>
            Dashboard
          </Button>
          <Button variant="ghost" onClick={() => navigate('/watchlist')}>
            Watchlist
          </Button>
          
          {user && (
            <div className="flex items-center gap-3 ml-4">
              <Avatar className="h-8 w-8">
                <AvatarFallback className="bg-primary text-primary-foreground">
                  {user.email[0].toUpperCase()}
                </AvatarFallback>
              </Avatar>
              <Button variant="ghost" size="sm" onClick={onSignOut}>
                <LogOut className="h-4 w-4" />
              </Button>
            </div>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;