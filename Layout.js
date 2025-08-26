
import React from "react";
import { Link, useLocation } from "react-router-dom";
import { createPageUrl } from "@/utils";
import { 
  LayoutDashboard, 
  Users, 
  Stethoscope, // Changed from UserMd to Stethoscope
  Calendar,
  Heart,
  Activity
} from "lucide-react";
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarHeader,
  SidebarProvider,
  SidebarTrigger,
} from "@/components/ui/sidebar";

const navigationItems = [
  {
    title: "Dashboard",
    url: createPageUrl("Dashboard"),
    icon: LayoutDashboard,
  },
  {
    title: "Patients", 
    url: createPageUrl("Patients"),
    icon: Users,
  },
  {
    title: "Doctors",
    url: createPageUrl("Doctors"), 
    icon: Stethoscope, // Changed from UserMd to Stethoscope
  },
  {
    title: "Appointments",
    url: createPageUrl("Appointments"),
    icon: Calendar,
  },
];

export default function Layout({ children, currentPageName }) {
  const location = useLocation();

  return (
    <SidebarProvider>
      <div className="min-h-screen flex w-full bg-gradient-to-br from-slate-50 to-blue-50">
        <style>{`
          :root {
            --medical-primary: #2563eb;
            --medical-secondary: #0891b2;
            --medical-success: #059669;
            --medical-accent: #7c3aed;
          }
        `}</style>
        
        <Sidebar className="border-r border-slate-200/60 bg-white/80 backdrop-blur-sm">
          <SidebarHeader className="border-b border-slate-200/60 p-6">
            <div className="flex items-center gap-3">
              <div className="relative">
                <div className="w-10 h-10 bg-gradient-to-br from-blue-500 to-blue-600 rounded-xl flex items-center justify-center shadow-lg">
                  <Heart className="w-6 h-6 text-white" />
                </div>
                <div className="absolute -top-1 -right-1 w-4 h-4 bg-green-400 rounded-full flex items-center justify-center">
                  <Activity className="w-2 h-2 text-white" />
                </div>
              </div>
              <div>
                <h2 className="font-bold text-slate-800 text-lg">MediCare</h2>
                <p className="text-xs text-slate-500 font-medium">Hospital Management</p>
              </div>
            </div>
          </SidebarHeader>
          
          <SidebarContent className="p-4">
            <SidebarGroup>
              <SidebarGroupLabel className="text-xs font-semibold text-slate-400 uppercase tracking-wider px-3 py-2 mb-2">
                Navigation
              </SidebarGroupLabel>
              <SidebarGroupContent>
                <SidebarMenu className="space-y-1">
                  {navigationItems.map((item) => (
                    <SidebarMenuItem key={item.title}>
                      <SidebarMenuButton 
                        asChild 
                        className={`group relative overflow-hidden rounded-xl transition-all duration-300 ${
                          location.pathname === item.url 
                            ? 'bg-gradient-to-r from-blue-500 to-blue-600 text-white shadow-lg' 
                            : 'hover:bg-slate-100 text-slate-600 hover:text-slate-800'
                        }`}
                      >
                        <Link to={item.url} className="flex items-center gap-3 px-4 py-3">
                          <item.icon className="w-5 h-5" />
                          <span className="font-medium">{item.title}</span>
                          {location.pathname === item.url && (
                            <div className="absolute inset-0 bg-gradient-to-r from-blue-400/20 to-blue-600/20 rounded-xl" />
                          )}
                        </Link>
                      </SidebarMenuButton>
                    </SidebarMenuItem>
                  ))}
                </SidebarMenu>
              </SidebarGroupContent>
            </SidebarGroup>
          </SidebarContent>
        </Sidebar>

        <main className="flex-1 flex flex-col">
          <header className="bg-white/80 backdrop-blur-sm border-b border-slate-200/60 px-6 py-4 md:hidden">
            <div className="flex items-center gap-4">
              <SidebarTrigger className="hover:bg-slate-100 p-2 rounded-lg transition-colors" />
              <div className="flex items-center gap-2">
                <Heart className="w-6 h-6 text-blue-500" />
                <h1 className="text-xl font-bold text-slate-800">MediCare</h1>
              </div>
            </div>
          </header>

          <div className="flex-1 overflow-auto">
            {children}
          </div>
        </main>
      </div>
    </SidebarProvider>
  );
}
